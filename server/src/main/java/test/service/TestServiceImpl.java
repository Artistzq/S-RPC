package test.service;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author : Artis Yao
 */
@Slf4j
public class TestServiceImpl implements TestService {

    @Override
    /**
     * 模拟一个服务，传入message，传出字符串
     */
    public String hello(Message message) {
        log.info("HelloServiceImpl收到消息：{}.", message.getContent());
        String result = "消息Description是" + message.getDescription();
        log.info("HelloServiceImpl返回消息：{}", result);
        return result;
    }

    @Override
    public String cat(Message message) {
        log.info("cat message");
        return message.getDescription() + message.getContent();
    }

}
