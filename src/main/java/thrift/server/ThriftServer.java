package thrift.server;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import thrift.PersonServiceImpl;
import thrift.generated.PersonService;

public class ThriftServer {
    public static void main(String[] args) throws Exception{
        //异步非阻塞
        TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
        THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());
        //这是协议层TCompactProtocol压缩协议
        //TBinarypRrotocol二进制格式协议
        //TJsonProtocol json格式协议
        //TSimpleJSONProtocol提供json只写协议生成的文件很容易通过脚本语言编译
        //TDebugProtocol 容易的可读文本格式方便debug
        arg.protocolFactory(new TCompactProtocol.Factory());
        //TSocket阻塞性的
        //TFramedTransport这是传输层用到的对象 以frame为传输单位非阻塞服务中使用
        //TFileTransport以文件方式运输
        //TMemoryTransport将内存用于io java内部实际使用了简单的ByteArrayOutPutStream
        //TZlibTransport使用zlib进行压缩与其他方式联合使用 但是现在没有java的实现
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));
        //服务模型
        //TSimpleServer 简单的单线程服务模型常用于测试
        //TThreadPoolServer 多线程服务模型标准阻塞io
        //TNonblockingServer 多线程服务模型使用非阻塞需要搭配TFramedTransport
        //THsHaServer THsHa引入线程池去处理其模型把读写任务放在线程池上面处理 Half-sync/Half-async 后者处理io 前者处理同步
        TServer server = new THsHaServer(arg);
        System.out.println("server is startes-------------");
        server.serve();
    }
}
