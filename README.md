# Pos_Tech_Fase_1

Para a entrega do 1º tech challenge, foram criadas 3 APIs, cada qual com seus métodos CRUD associados:

1. Cadastro de Eletrodomésticos
2. Cadastro de Pessoas
3. Cadastro de Endereços

Foram utilizadas as seguintes tecnologias/técnicas:
 * **Java + Spring** - recebimento e processamento de requisições
 * **Banco de dados _in-memory_ H2** - armazenamento das informações
 * **Jakarta** - validação das informações de entrada (não aceitar campos nulos, vazios, ...)
 * **Exception Handler** - personalizado para tratamento das exceções geradas no processo
 * **DDD** - desenvolvimento com base nas regras de domínio
 * **Postman** - geração das requisições e validação da API

Ressalta-se como principal desafio, ainda não resolvido, a repetição de código entre as API's. As funções CRUD entre as API's possuem códigos semelhantes, que poderiam ser abstraídos. Tentou-se abstrair os códigos utilizando uma classe abstrata e um repository genérico, mas houveram muitos problemas na conversão dos objetos retornados pelo repository genérico entre as três categorias (eletrodoméstico, pessoa ou endereço).  Assim, optou-se por prosseguir com as três API's separadas.

Outro desafio foi a utilização do JMapper, que não possui compatibilidade com a versão JAVA utilizada. Para tanto, foram desenvolvidos os métodos toEndereco, toPessoa e toEletrodomestico nos serviços respectivos. Estes métodos possibilitaram travar parâmetros de entrada pelo usuário, impedindo que o usuário pudesse configurar o id dos objetos, por exemplo.

## Descrição das APIs

### Cadastro de Eletrodomésticos

A API cadastro de eletrodomésticos permite armazenar as seguintes informações: nome, modelo e potência.
