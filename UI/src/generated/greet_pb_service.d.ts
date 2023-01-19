// package: greet
// file: greet.proto

import * as greet_pb from "./greet_pb";
import {grpc} from "@improbable-eng/grpc-web";

type GreeterSayHello = {
  readonly methodName: string;
  readonly service: typeof Greeter;
  readonly requestStream: true;
  readonly responseStream: true;
  readonly requestType: typeof greet_pb.Message;
  readonly responseType: typeof greet_pb.Message;
};

type GreeterIsTyping = {
  readonly methodName: string;
  readonly service: typeof Greeter;
  readonly requestStream: true;
  readonly responseStream: true;
  readonly requestType: typeof greet_pb.Typing;
  readonly responseType: typeof greet_pb.Typing;
};

export class Greeter {
  static readonly serviceName: string;
  static readonly SayHello: GreeterSayHello;
  static readonly IsTyping: GreeterIsTyping;
}

export type ServiceError = { message: string, code: number; metadata: grpc.Metadata }
export type Status = { details: string, code: number; metadata: grpc.Metadata }

interface UnaryResponse {
  cancel(): void;
}
interface ResponseStream<T> {
  cancel(): void;
  on(type: 'data', handler: (message: T) => void): ResponseStream<T>;
  on(type: 'end', handler: (status?: Status) => void): ResponseStream<T>;
  on(type: 'status', handler: (status: Status) => void): ResponseStream<T>;
}
interface RequestStream<T> {
  write(message: T): RequestStream<T>;
  end(): void;
  cancel(): void;
  on(type: 'end', handler: (status?: Status) => void): RequestStream<T>;
  on(type: 'status', handler: (status: Status) => void): RequestStream<T>;
}
interface BidirectionalStream<ReqT, ResT> {
  write(message: ReqT): BidirectionalStream<ReqT, ResT>;
  end(): void;
  cancel(): void;
  on(type: 'data', handler: (message: ResT) => void): BidirectionalStream<ReqT, ResT>;
  on(type: 'end', handler: (status?: Status) => void): BidirectionalStream<ReqT, ResT>;
  on(type: 'status', handler: (status: Status) => void): BidirectionalStream<ReqT, ResT>;
}

export class GreeterClient {
  readonly serviceHost: string;

  constructor(serviceHost: string, options?: grpc.RpcOptions);
  sayHello(metadata?: grpc.Metadata): BidirectionalStream<greet_pb.Message, greet_pb.Message>;
  isTyping(metadata?: grpc.Metadata): BidirectionalStream<greet_pb.Typing, greet_pb.Typing>;
}

