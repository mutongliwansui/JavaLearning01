package com.mutongli;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class TestNioSocket {
    public static void main(String[] args) {
        try {
            //创建ServerSocketChannel，监听8080端口
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            InetSocketAddress socketAddress = new InetSocketAddress(8080);
            socketChannel.socket().bind(socketAddress);
            //设置为非阻塞模式
            socketChannel.configureBlocking(false);
            //为socketChannel注册选择器
            Selector  selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //创建处理器
            while (true){
                //等待请求，每次等待阻塞3s，超过3s后线程继续向下运行，如果传入0或者不传参数将一直阻塞
                if(selector.select(3000) == 0){
                    continue;
                }
                //获取待处理的SelectionKey
                Iterator<SelectionKey> keyiter = selector.selectedKeys().iterator();

                while (keyiter.hasNext()){
                    SelectionKey key = keyiter.next();
                    //启动新线程处理SelectionKey
                    HttpHandler handler = new HttpHandler(key);
                    //处理完后，从待处理的SelectionKey迭代器中移除当前使用的key
                    new Thread(handler).run();
                    keyiter.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static class HttpHandler implements Runnable{
        private int bufferSize = 1024;
        private String localCharset = "UTF-8";
        private SelectionKey key;

        public HttpHandler(SelectionKey key) {
            this.key = key;
        }

        public void handleAccept() throws IOException{
            SocketChannel cilentChannel = ((ServerSocketChannel)key.channel()).accept();
            cilentChannel.configureBlocking(false);
            cilentChannel.register(key.selector(),SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
        }

        public void handleRead() throws IOException{
            //获取channel
            SocketChannel sc = (SocketChannel) key.channel();
            //获取buffer并重置
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.clear();
            //没有读取到内容则关闭
            if(sc.read(buffer) == -1){
                sc.close();
            }else{
                //接收请求数据
                buffer.flip();
                String receivedStr = Charset.forName(localCharset).newDecoder().decode(buffer).toString();
                //控制台打印请求报文头
                String [] requestMessage = receivedStr.split("\r\n");
                for (String s :
                        requestMessage) {
                    System.out.println(s);
                    //遇到空行说明报文头已经打印完
                    if(s.isEmpty()){
                        break;
                    }
                }
                //控制台打印首行信息
                String [] firstLine = requestMessage[0].split(" ");
                System.out.println();
                System.out.println("Method:\t"+firstLine[0]);
                System.out.println("url:\t"+firstLine[1]);
                System.out.println("HTTP Version:\t"+firstLine[2]);
                System.out.println();

                //返回客户端
                StringBuilder sendStr = new StringBuilder();
                sendStr.append("HTTP/1.1 200 OK\r\n");
                sendStr.append("Content-Type:text/html;charset="+localCharset+"\r\n");
                sendStr.append("\r\n");

                sendStr.append("<html><head><title>显示报文</title></head><body>");
                sendStr.append("接收到请求报文是：</br>");
                for (String s :
                        requestMessage) {
                    sendStr.append(s + "<br/>");
                }
                sendStr.append("</body></html>");
                buffer = ByteBuffer.wrap(sendStr.toString().getBytes(localCharset));
                sc.write(buffer);
                sc.close();
            }
        }

        @Override
        public void run() {
                try {
                    //接收到连接请求时
                    if(key.isAcceptable()){
                            handleAccept();
                    }
                    //读数据
                    if(key.isReadable()){
                        handleRead();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
