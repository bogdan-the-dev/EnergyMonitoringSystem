using ChatService;
using Grpc.Core;

namespace ChatService.Services
{
    public class GreeterService : Greeter.GreeterBase
    {
        private readonly ILogger<GreeterService> _logger;
        private readonly Mapping mapping;
        private readonly TypingMapping _typing;
        public GreeterService(ILogger<GreeterService> logger, Mapping mapping, TypingMapping typing)
        {
            _logger = logger;
            this.mapping = mapping;
            _typing = typing;
        }

        public override async Task SayHello(IAsyncStreamReader<Message> requestStream, IServerStreamWriter<Message> responseStream, ServerCallContext context)
        {
            this.mapping.mapping[context.RequestHeaders.Get("sender").Value] = responseStream;
            var sender = context.RequestHeaders.Get("sender").Value.ToString();
            while (await requestStream.MoveNext())
            {
                var message = new Message();
                message.Username = sender;
                message.Message_ = requestStream.Current.Message_;
                if(this.mapping.mapping.ContainsKey(requestStream.Current.Username))
                {
                    try
                    {
                        await this.mapping.mapping[requestStream.Current.Username].WriteAsync(message);
                    }
                    catch { }
                }
            }
            await Task.Delay(Timeout.Infinite);
            this.mapping.mapping.Remove(context.RequestHeaders.Get("sender").Value, out var output);
        }

        public override async Task IsTyping(IAsyncStreamReader<Typing> requestStream, IServerStreamWriter<Typing> responseStream, ServerCallContext context)
        {
            _typing.Type[context.RequestHeaders.Get("sender").Value] = responseStream;
            var sender = context.RequestHeaders.Get("sender").Value.ToString();
            while(await requestStream.MoveNext())
            {
                var type = new Typing();
                type.Username = sender;
                if(_typing.Type.ContainsKey(requestStream.Current.Username))
                {
                    try 
                    {
                    await _typing.Type[requestStream.Current.Username].WriteAsync(type);
                    }
                    catch{ }
                }
            }
            await Task.Delay(Timeout.Infinite);
            _typing.Type.Remove(context.RequestHeaders.Get("sender").Value, out var dummy);
        }
    }
}