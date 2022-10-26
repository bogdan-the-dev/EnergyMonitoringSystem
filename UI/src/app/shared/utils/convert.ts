import {ActionWithPayload} from "../model/action-with-payload.model";

export class Convert {
  static ToPayload(action: ActionWithPayload) : any {
    return action.payload
  }
}
