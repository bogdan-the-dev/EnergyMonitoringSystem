import {Injectable} from "@angular/core";
import {AnonymousSubject, Subject} from "rxjs/internal/Subject";
import {connect, map, Observable, Observer} from "rxjs";
import SockJS from 'sockjs-client';

export interface Message {
  deviceOwnerId: number
  deviceId: number
  message: string
}


@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  public subject: Subject<MessageEvent>
  public message

  constructor() {


  }
  public connect(url): AnonymousSubject<MessageEvent> {
    if (!this.subject) {
      this.subject = this.create(url);
      console.log("Successfully connected: " + url);
    }
    return this.subject;
  }

  private create(url): AnonymousSubject<MessageEvent> {
    let ws = new WebSocket(url);
    let observable = new Observable((obs: Observer<MessageEvent>) => {
      ws.onmessage = obs.next.bind(obs);
      ws.onerror = obs.error.bind(obs);
      ws.onclose = obs.complete.bind(obs);
      return ws.close.bind(ws);
    });
    let observer = {
      error: null,
      complete: null,
      next: (data: Object) => {
        console.log('Message sent to websocket: ', data);
        if (ws.readyState === WebSocket.OPEN) {
          ws.send(JSON.stringify(data));
        }
      }
    };
    return new AnonymousSubject<MessageEvent>(observer, observable);
  }

}
