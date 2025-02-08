package showcase.smpp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class SMPPConfig {
    private static final Logger logger = LogManager.getLogger();

    public static Server getServerConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(SMPPConfig.class.getResourceAsStream("/server.yml"), Server.class);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static Client getClientConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(SMPPConfig.class.getResourceAsStream("/client.yml"), Client.class);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static class Server {
        private Integer port;

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }
    }

    public static class Client {
        private String ip;
        private Integer port;
        private Bind bind;
        private Submit submit;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public Bind getBind() {
            return bind;
        }

        public void setBind(Bind bind) {
            this.bind = bind;
        }

        public Submit getSubmit() {
            return submit;
        }

        public void setSubmit(Submit submit) {
            this.submit = submit;
        }
    }


    public static class Bind {
        private String account;
        private String passwd;
        private String systemType;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public String getSystemType() {
            return systemType;
        }

        public void setSystemType(String systemType) {
            this.systemType = systemType;
        }
    }

    public static class Submit {
        private String from;
        private String to;
        private Integer maxLength;
        private Integer splitLength;
        private String content;
        private Byte dcs;
        private Byte charset;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Byte getDcs() {
            return dcs;
        }

        public void setDcs(Byte dcs) {
            this.dcs = dcs;
        }

        public Byte getCharset() {
            return charset;
        }

        public void setCharset(Byte charset) {
            this.charset = charset;
        }

        public Integer getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(Integer maxLength) {
            this.maxLength = maxLength;
        }

        public Integer getSplitLength() {
            return splitLength;
        }

        public void setSplitLength(Integer splitLength) {
            this.splitLength = splitLength;
        }
    }
}
