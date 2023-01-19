using Grpc.Core;
using System.Collections.Concurrent;

namespace ChatService.Services
{
    public class TypingMapping
    {
        public ConcurrentDictionary<string, IServerStreamWriter<Typing>> Type { get; set; } = new ConcurrentDictionary<string, IServerStreamWriter<Typing>>();
    }
}
