dv2web
======
Objetivo deste projeto:

Converter vídeos para formato compatível com a WEB

Serviços utilizados no processo:

* AWS EC2 - Ambiente de execução desta aplicação.
* AWS S3 - Local de armazenamento para os vídeos enviados para conversão e também para os já convertidos.
* ENCODING.com - Serviço de conversão dos vídeo para o formato web.

Configuração de encoding para saida da conversão:

Formato de saída: wma
Bitrate: 256k
Codec de Vídeo: wmv2
Codec de Áudio: libmp3lame

Estratégias de Deploy Contínuo:

A estratégia consiste em consultar o repositório GiHub periodicamente e validar se existe alguma alteração em relação ao repositório clonado no servidor da aplicação.
Caso exista, o repositório local será atualizado e o build de construção e implantação executado.

Para permitir a atualização automatica da aplicação, as configurações do Tomcat foram alteradas conforme descrito na documentaçõ:
http://tomcat.apache.org/tomcat-5.5-doc/config/host.html#Automatic_Application_Deployment
