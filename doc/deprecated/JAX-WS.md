# Java Web Service学习
- 服务端
    - @WebService
        - SEI(server endpoint interface)
        - SEB(sever endpoint bean)
        - 将POJO转换为SOAP的Web服务端点
    - @SOAPBinding: 注解SEI、SEI方法
    - @WebMethod: 注解SEI中方法
    - @WebServiceProvider
        - Provider<Source>
        - Provider<SOAPMessage>
        - Provider<DataSource>
    - @RequestWapper: 注解SEI中方法
    - @ResponseWrapper: 注解SEI中方法
    - @WebFault: 注解在SEI抛出的异常类上
    - @Oneway: 注解在SEI不需要回应的方法上
    - @WebParam: 注解在SEI方法参数上
    - @WebResult: 注解在SEI方法上
    - @WebServiceRef
    ```java
    @WebService(
        endpointInterface   // 服务端点的完全限定类名
        name                // Web服务名称(wsdl:portType)
        portName            // Web服务端点(wsdl:port)
        serviceName         // Web服务服务名称(wsdl:service)
        targetNamespace     // Web服务的名称空间(targetNamespace)
        wsdlLocation        // 预定义的WSDL服务描述定位符
    )
    @WebMethod(
        exclude=true //排除在服务端点外
    )
    @WebServiceProvider(
        portName            // 端口名称
        serviceName         // 服务名称
        targetNamespace     // 服务的目标名称
        wsdlLocation        // wsdl位置
    )
    // 部署
    Endpoint.publish("xxx",xxx);
    ```
- 客户端
    - @WebServiceClient
        - 继承Service
        - 返回Port实现BindingProvider接口
            - ENDPOINT_ADDRESS_PROPERTY
            - USERNAME_PROPERTY
            - PASSWORD_PROPERTY
            - SESSION_MAINTAIN_PROPERTY
            - SOAPACTION_USE_PROPERTY
            - SOAPACTION_URI_PROPERTY
    ```java
    @WebServiceClient(
        name="xx",
        targetNamespace="xx",
        wsdlLocation="xx"
    )
    // 基于Dispatch
    QName serviceName = new QName("xx");
    Service service = Serice.create(serviceName);
    QName portName = new QName("xxx");
    Dispatch<Source> dispatch = serivce.createDispatch(portName,Source.class,Service.Mode.PAYLOAD);
    Source source = new StreamSource(...);
    Source response = dispatch.invoke(source);
    ```    
- JWS Handler(责任链设计模式,AOP)
    - 实现javax.xml.ws.Handle接口
        - LogicalHandler
        - SOAPHandler
    - 配置文件或代码组成处理链