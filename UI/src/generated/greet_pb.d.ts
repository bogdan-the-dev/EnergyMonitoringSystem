// package: greet
// file: greet.proto

import * as jspb from "google-protobuf";

export class Message extends jspb.Message {
  getUsername(): string;
  setUsername(value: string): void;

  getMessage(): string;
  setMessage(value: string): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): Message.AsObject;
  static toObject(includeInstance: boolean, msg: Message): Message.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: Message, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): Message;
  static deserializeBinaryFromReader(message: Message, reader: jspb.BinaryReader): Message;
}

export namespace Message {
  export type AsObject = {
    username: string,
    message: string,
  }
}

export class Typing extends jspb.Message {
  getUsername(): string;
  setUsername(value: string): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): Typing.AsObject;
  static toObject(includeInstance: boolean, msg: Typing): Typing.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: Typing, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): Typing;
  static deserializeBinaryFromReader(message: Typing, reader: jspb.BinaryReader): Typing;
}

export namespace Typing {
  export type AsObject = {
    username: string,
  }
}

