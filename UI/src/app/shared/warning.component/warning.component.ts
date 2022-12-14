import {Component, OnInit} from "@angular/core";
import {Message, WebsocketService} from "../../service/websocketService";
import {AlertifyService} from "../../service/alertifyService";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

export interface message {
  deviceOwnerUsername: string
  deviceId: number
  message: string
}

@Component({
  selector: 'app-consumption-warning',
  templateUrl: 'warning.component.html',
  styleUrls: ['warning.component.less'],
  providers: [AlertifyService]
})
export class WarningComponent{

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
      console.log('Connected')
      that.stompClient.subscribe("/chat/info", (message) => {
        if(message.body) {
          const msg: message = JSON.parse(message.body)
          if(sessionStorage.getItem("username") !== undefined && msg.deviceOwnerUsername === sessionStorage.getItem("username")) {
            that.alertifyService.warning(msg.message)
          }
        }
      })
    })
  }
}
