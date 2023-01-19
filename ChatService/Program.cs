using ChatService.Services;

var builder = WebApplication.CreateBuilder(args);

// Additional configuration is required to successfully run gRPC on macOS.
// For instructions on how to configure Kestrel and gRPC clients on macOS, visit https://go.microsoft.com/fwlink/?linkid=2099682

// Add services to the container.
builder.Services.AddGrpc();
builder.Services.AddSingleton<Mapping>();
builder.Services.AddSingleton<TypingMapping>();
builder.Services.AddCors((name) =>
{
    name.AddPolicy("security", (builder) =>
    {
        builder.AllowAnyHeader().AllowAnyMethod().AllowAnyOrigin();
    });
});

var app = builder.Build();
app.UseGrpcWeb();
app.UseCors((name) =>
{
    name.AllowAnyHeader().AllowAnyMethod().AllowAnyOrigin();
});
// Configure the HTTP request pipeline.
app.MapGrpcService<GreeterService>().EnableGrpcWeb();
app.MapGet("/", () => "Communication with gRPC endpoints must be made through a gRPC client. To learn how to create a client, visit: https://go.microsoft.com/fwlink/?linkid=2086909");

app.Run();
