import {Component, OnInit} from "@angular/core";
import {Message, WebsocketService} from "../../service/websocketService";
import {AlertifyService} from "../../service/alertifyService";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';


@Component({
  selector: 'app-consumption-warning',
  templateUrl: 'warning.component.html',
  styleUrls: ['warning.component.less']
})
export class WarningComponent implements OnInit{

  private stompClient

  received = []

  constructor(private websocketService: WebsocketService, private alertifyService: AlertifyService) {
    // this.websocketService.connect('ws://localhost:8080').subscribe(res => {
    //   this.received.push(res);
    //   console.log(res.data)
    //   //alertifyService.warning(res);
    //
    // })
    this.initWS()

  }

  initWS(){
    let ws = new SockJS("http://localhost:8080/socket")
    this.stompClient = Stomp.over(ws)
    let that = this
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe("/chat", (message) => {
        if(message.body) {
        console.log(message.body)
        }
      })
    })
  }

  ngOnInit(): void {

  }



}
