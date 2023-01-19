using Grpc.Core;
using System.Collections.Concurrent;

namespace ChatService.Services
{
    public class Mapping
    {
        public ConcurrentDictionary<string, IServerStreamWriter<Message>> mapping = new ConcurrentDictionary<string, IServerStreamWriter<Message>>();
    }
}
