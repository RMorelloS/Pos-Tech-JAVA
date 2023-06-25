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

1. Para gravar um eletrodoméstico, utilizar uma requisição do tipo POST, passando informações como:
```bash
curl --location 'localhost:8080/eletrodomestico' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "Fogão",
    "potencia": 238,
    "modelo": "Electrolux",
    "idEletrodomestico": "aaaa"
}'
```
Nesta requisição, ressalta-se que apenas os campos permitidos são cadastrados. No exemplo, há a tentativa de submeter um id pelo usuário, que é bloqueado pelo uso de DTO's. 

**Saída: retorno 200 - OK ou erro de validação, caso algum dos campos não atenda aos requisitos necessários**

**Em caso de sucesso:**
![image](https://github.com/RMorelloS/Pos_Tech_Fase_1/assets/32580031/39768dba-e002-4f82-a7c1-01c0648f11ee)

**Em caso de erro:**

```json
{
    "timestamp": "2023-06-25T21:19:45.789570700Z",
    "status": null,
    "error": "Erro na validação de campos",
    "message": "Erro na validação dos campos: {nome=Campo 'nome' é obrigatório e não pode estar vazio}",
    "path": "/eletrodomestico"
}
```

2. Para ler os eletrodomésticos cadastrados, utilizar uma requisição do tipo GET:

```bash
curl --location 'localhost:8080/eletrodomestico'
```

**Saída: retorna os eletrodomésticos cadastrados**

```json
[
    {
        "nome": "Fogão",
        "potencia": 238,
        "modelo": "Electrolux",
        "idEletrodomestico": "ddf0097f-d55c-421d-a36d-fe962ab26cbb"
    }
]
```

3. Para ler um eletrodoméstico específico, utilizar uma requisição do tipo GET, passando um id como parâmetro:

```bash
curl --location 'localhost:8080/eletrodomestico/c88f374b-7d7f-4f7b-a484-3d80301d2134'
```

**Saída: retorna o eletrodoméstico ou mensagem de erro, caso não haja um eletrodoméstico com o id especificado**

```json
{
    "nome": "Fogão",
    "potencia": 238,
    "modelo": "Electrolux",
    "idEletrodomestico": "ddf0097f-d55c-421d-a36d-fe962ab26cbb"
}
```

4. Para atualizar um eletrodoméstico, utilizar uma requisição do tipo PUT, passando as informações, incluindo o id do objeto a ser atualizado:

```bash
curl --location --request PUT 'localhost:8080/eletrodomestico' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "Geladeira",
    "potencia": 110,
    "modelo": "Electrolux",
    "idEletrodomestico": "c88f374b-7d7f-4f7b-a484-3d80301d2134"
}'
```
**Saída: objeto eletrodoméstico atualizado**

```json
{
    "nome": "Geladeira",
    "potencia": 110,
    "modelo": "Electrolux",
    "idEletrodomestico": "ddf0097f-d55c-421d-a36d-fe962ab26cbb"
}
```


5. Para excluir um eletrodoméstico, utilizar uma requisição do tipo DELETE, passando um id como parâmetro:
```bash
curl --location --request DELETE 'localhost:8080/eletrodomestico/c88f374b-7d7f-4f7b-a484-3d80301d2134'
```
**Saída: retorna 200 - OK ou mensagem de erro, caso não haja um eletrodoméstico com o id especificado**

![image](https://github.com/RMorelloS/Pos_Tech_Fase_1/assets/32580031/5d2e2132-f3eb-4fa3-9113-253bbade14d5)


