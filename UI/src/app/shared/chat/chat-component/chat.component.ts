import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
import {BidirectionalStream, GreeterClient} from "../../../../generated/greet_pb_service";
import {Message, Typing} from "../../../../generated/greet_pb";
import {grpc} from "@improbable-eng/grpc-web";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-chat-component',
  templateUrl: 'chat.component.html',
  styleUrls: ['chat.component.less']
})
export class ChatComponent implements OnInit{
  stream: BidirectionalStream<Message, Message>
  typingStream: BidirectionalStream<Typing, Typing>
  destinations: string[] = []
  messages: Map<string, Message[]> = new Map<string, Message[]>()

  selectedChat = []
  selectedUsername = ''

  isTyping: boolean = false

  typingTimeout

  @ViewChild('textArea') textArea!: ElementRef;


  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    const header = new grpc.Metadata()
    header.set('sender', sessionStorage.getItem('username'))
    const client =new GreeterClient("https://localhost:49155")
    this.stream = client.sayHello(header)
    this.stream.on("data", (message) => {
      console.log(message)
      if(!this.messages.has(message.getUsername())) {
        this.messages.set(message.getUsername(), [])
      }
      console.log(this.messages)
      this.messages.get(message.getUsername()).push(message)
    })

    this.typingStream = client.isTyping(header)
    this.typingStream.on("data", (typing) => {
      console.log(typing)
      this.isTyping = true
      if(typing.getUsername() === sessionStorage.getItem('username')) {
        clearTimeout(this.typingTimeout)
        this.typingTimeout = setTimeout(() => {
          this.isTyping = false
        }, 0.5)
      }
    })

    if(!(sessionStorage.getItem('isAdmin') === 'true')) {
      this.userService.getAdmins().subscribe((res: []) => {
        res.filter((elem: any) => elem.username != sessionStorage.getItem('username')).forEach((elem: any) => {
          this.destinations.push(elem.username)
          this.messages.set(elem.username, [])
        })
      })
    }
    else {
      this.userService.getRegular().subscribe((res: []) => {
        res.forEach((elem: any) => {
          this.destinations.push(elem.username)
          this.messages.set(elem.username, [])
        })
      })
    }

    this.stream.on("end", () => {
      console.log("closed!!")
      this.stream = client.sayHello(header)
    })

    this.typingStream.on("end", () => {
      this.typingStream = client.isTyping(header)
    })
  }

  selectConversation(username) {
    this.selectedUsername = username
    const conv = this.messages.get(username)
    if (conv) {
      this.selectedChat = conv
    }
  }


  onSend(text) {

    const message = new Message()
    message.setMessage(text)
    message.setUsername(this.selectedUsername)
    this.stream.write(message)

    const newEntry = new Message()
    newEntry.setUsername(sessionStorage.getItem('username'))
    newEntry.setMessage(text)
    this.selectedChat.push(newEntry)

  }

  sendIsTyping(): void {
    const isTyping = new Typing()
    isTyping.setUsername(this.selectedUsername)
    this.typingStream.write(isTyping)
  }

}
