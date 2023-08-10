# Pós Tech - Fase 2

Para a entrega do 2º tech challenge, foram criadas 4 APIs, cada qual com seus métodos CRUD associados:

1. Cadastro de Eletrodomésticos
2. Cadastro de Pessoas
3. Cadastro de Endereços
4. Cadastro de usuários

Foram utilizadas as seguintes tecnologias/técnicas:
 * **Java + Spring** - recebimento e processamento de requisições
 * **Banco de dados PostgreSQL** - armazenamento das informações
 * **Jakarta** - validação das informações de entrada (não aceitar campos nulos, vazios, ...)
 * **Exception Handler** - personalizado para tratamento das exceções geradas no processo
 * **DDD** - desenvolvimento com base nas regras de domínio
 * **Postman** - geração das requisições e validação da API
 * **Docker** - definição de containers e virtualização

Ressalta-se como principal desafio, ainda não resolvido, a repetição de código entre as API's. As funções CRUD entre as API's possuem códigos semelhantes, que poderiam ser abstraídos. Tentou-se abstrair os códigos utilizando uma classe abstrata e um repository genérico, mas houveram muitos problemas na conversão dos objetos retornados pelo repository genérico entre as três categorias (eletrodoméstico, pessoa ou endereço).  Assim, optou-se por prosseguir com as três API's separadas.

Outro desafio foi a utilização do JMapper, que não possui compatibilidade com a versão JAVA utilizada. Para tanto, foram desenvolvidos os métodos toEndereco, toPessoa, toEletrodomestico e toUsuario nos serviços respectivos. Estes métodos possibilitaram travar parâmetros de entrada pelo usuário, impedindo que o usuário pudesse configurar o id dos objetos, por exemplo.

# Diagrama de classes

O projeto foi desenvolvido considerando quatro entidades principais: 
1. Usuários
2. Endereços
3. Pessoas
4. Eletrodomésticos
   
Considerou-se que um usuário pode ter múltiplos endereços associados, mas um endereço é associado a apenas um usuário.
Para cada endereço, podem haver N pessoas ou N aparelhos, todos associados a um único endereço.

O método _findByParam_ permite retornar os registros por determinado filtro, como rua ou cidade, no caso da tabela endereço.

O método _buscarPorEndereco_ permite retornar os aparelhos ou pessoas associados a determinado endereço. 

A alteração dos objetos na relação 1 x N foi configurada para o modo cascata. Por exemplo, a exclusão do usuário deletará todos os endereços associados.

![diagrama_classes_techchallenge](https://github.com/RMorelloS/Pos-Tech-JAVA/assets/32580031/85108503-b5fd-4183-8e67-ecc908a35e08)

# Descrição das APIs

## Usuário

A API cadastro de usuários permite armazenar o login do usuário e serve como _endpoint_ principal para manipular as demais entidades.

### 1. Para gravar um usuário, utilizar uma requisição do tipo POST, passando informações como:

```bash
curl --location 'localhost:8080/usuario' \
--header 'Content-Type: application/json' \
--data '{
    "loginUsuario": "ricardoms",
    "nome": "Ricardo Morello"
}'
```

**Saída: retorno 200 - OK ou erro de validação, caso algum dos campos não atenda aos requisitos necessários**
**Em caso de sucesso:**
![image](https://github.com/RMorelloS/Pos-Tech-JAVA/assets/32580031/450ee559-e990-4f2a-a458-280ad1bd8831)

**Em caso de erro:**
```bash
{
    "timestamp": "2023-08-10T01:25:21.462286100Z",
    "status": null,
    "error": "Erro na validação de campos",
    "message": "Erro na validação dos campos: {loginUsuario=Campo 'loginUsuario' é obrigatório e não pode estar vazio}",
    "path": "/usuario"
}
```
### 2. Para ler os usuários cadastrados, utilizar uma requisição do tipo GET:

```bash
curl --location 'localhost:8080/usuario'
```
**Saída: retorna os usuários cadastrados**

```bash
[
    {
        "idUsuario": "c26f3663-3b1d-4762-acea-81f90e5cf9f8",
        "loginUsuario": "ricardoms"
    }
]
```

### 3. Para ler as informações de um usuário específico, utilizar uma requisição do tipo GET, passando um id como parâmetro:

```bash
curl --location 'localhost:8080/usuario/c26f3663-3b1d-4762-acea-81f90e5cf9f8'
```

**Saída: retorna o usuário ou mensagem de erro, caso não haja um usuário com o id especificado**

```bash
{
    "idUsuario": "c26f3663-3b1d-4762-acea-81f90e5cf9f8",
    "loginUsuario": "ricardoms"
}
```

### 4. Para atualizar as informações de um usuário, utilizar uma requisição do tipo PUT, passando as informações, incluindo o id do objeto a ser atualizado:

```bash
curl --location --request PUT 'localhost:8080/usuario' \
--header 'Content-Type: application/json' \
--data '{
    "loginUsuario": "Usuário trocado",
    "idUsuario": "c26f3663-3b1d-4762-acea-81f90e5cf9f8"
}
'
```

**Saída: objeto endereço atualizado**

```json
{
    "idUsuario": "c26f3663-3b1d-4762-acea-81f90e5cf9f8",
    "loginUsuario": "Usuário trocado",
    "hibernateLazyInitializer": {}
}
```

### 5. Para excluir um usuário, utilizar uma requisição do tipo DELETE, passando um id como parâmetro:

```bash
curl --location --request DELETE 'localhost:8080/usuario/c26f3663-3b1d-4762-acea-81f90e5cf9f8'
```

**Saída: retorna 200 - OK ou mensagem de erro, caso não haja um usuário com o id especificado**

![image](https://github.com/RMorelloS/Pos-Tech-JAVA/assets/32580031/14449ef1-d0f6-4650-8c3c-c15bbdc43722)

### 6.

## Cadastro de Endereços 

A API cadastro de endereços permite armazenar as seguintes informações: rua, numero, bairro, cidade e estado.

### 1. Para gravar um endereço, utilizar uma requisição do tipo POST, passando informações como:
**Obs: Necessário informar o id do usuário ao qual o endereço será atrelado** 

```bash
curl --location 'localhost:8080/endereco' \
--header 'Content-Type: application/json' \
--data '{
    "rua": "Avenida 1",
    "numero": 20,
    "bairro": "Bairro 1",
    "cidade": "São Paulo",
    "estado": "São Paulo",
    "usuario": {
        "idUsuario":"88323b9b-38dc-4d8a-8a2b-9371282b0d51"
    }
}'
```

**Saída: retorno 200 - OK ou erro de validação, caso algum dos campos não atenda aos requisitos necessários ou caso não exista um usuário com o id informado**

**Em caso de sucesso:**
![image](https://github.com/RMorelloS/Pos_Tech_Fase_1/assets/32580031/4b45f0c4-e2a9-44ab-a3f1-aa86d14524a4)

**Em caso de erro:**
```bash
{
    "timestamp": "2023-06-25T21:44:51.839980600Z",
    "status": null,
    "error": "Erro na validação de campos",
    "message": "Erro na validação dos campos: {numero=Campo 'numero' deve ser um inteiro positivo}",
    "path": "/endereco"
}
```

### 2. Para ler os endereços cadastrados, utilizar uma requisição do tipo GET:

```bash
curl --location 'localhost:8080/endereco'
```

**Saída: retorna os endereços cadastrados**

```bash
[
    {
        "rua": "Avenida 1",
        "numero": 20,
        "bairro": "Bairro 1",
        "cidade": "São Paulo",
        "estado": "São Paulo",
        "idEndereco": "5f6fc85b-d5cd-4a8c-b633-4a90c10f9501",
        "usuario": {
            "idUsuario": "88323b9b-38dc-4d8a-8a2b-9371282b0d51",
            "loginUsuario": "ricardoms"
        }
    }
]
```

### 3. Para ler as informações de um endereço específico, utilizar uma requisição do tipo GET, passando um id como parâmetro:

```bash
curl --location 'localhost:8080/endereco/ced14866-276c-4cc3-b03a-72fb1da182d4'
```
**Saída: retorna o endereço ou mensagem de erro, caso não haja um endereço com o id especificado**

```bash
{
    {
        "rua": "Avenida 1",
        "numero": 20,
        "bairro": "Bairro 1",
        "cidade": "São Paulo",
        "estado": "São Paulo",
        "idEndereco": "5f6fc85b-d5cd-4a8c-b633-4a90c10f9501",
        "usuario": {
            "idUsuario": "88323b9b-38dc-4d8a-8a2b-9371282b0d51",
            "loginUsuario": "ricardoms"
        }
    }
}
```

### 4. Para atualizar as informações de um endereço, utilizar uma requisição do tipo PUT, passando as informações, incluindo o id do objeto a ser atualizado:

```bash
curl --location --request PUT 'localhost:8080/endereco' \
--header 'Content-Type: application/json' \
--data '{
    "rua": "Avenida 2",
    "numero": 20,
    "bairro": "Bairro 2",
    "cidade": "São Paulo",
    "estado": "São Paulo",
    "idEndereco":  "5f6fc85b-d5cd-4a8c-b633-4a90c10f9501",
    "usuario": {
        "idUsuario":"88323b9b-38dc-4d8a-8a2b-9371282b0d51"
    }
}'
```

**Saída: objeto endereço atualizado**

```json
{
    "rua": "Avenida 2",
    "numero": 20,
    "bairro": "Bairro 2",
    "cidade": "São Paulo",
    "estado": "São Paulo",
    "idEndereco": "5f6fc85b-d5cd-4a8c-b633-4a90c10f9501",
    "usuario": {
        "idUsuario": "88323b9b-38dc-4d8a-8a2b-9371282b0d51",
        "loginUsuario": "ricardoms"
    }
}
```

### 5. Para excluir um endereço, utilizar uma requisição do tipo DELETE, passando um id como parâmetro:

```bash
curl --location --request DELETE 'localhost:8080/endereco/5f6fc85b-d5cd-4a8c-b633-4a90c10f9501'
```

**Saída: retorna 200 - OK ou mensagem de erro, caso não haja um endereço com o id especificado**

![image](https://github.com/RMorelloS/Pos_Tech_Fase_1/assets/32580031/21ed6a8f-3279-4cbc-b136-d4c8eef0d396)



## Eletrodomésticos

A API cadastro de eletrodomésticos permite armazenar as seguintes informações: nome, modelo, potência, id do endereço associado e tempo de uso do aparelho.

### 1. Para gravar um eletrodoméstico, utilizar uma requisição do tipo POST, passando informações como:
```bash
curl --location 'localhost:8080/eletrodomestico' \
--header 'Content-Type: application/json' \
--data '{
    "nome":"Televisão",
    "potencia": 110,
    "modelo": "Electrolux",
    "endereco": {
        "idEndereco": "209f52c5-d1a2-443f-bd41-d8a0ee014f22"
    },
    "idUsuario": "ccac9e49-54ce-48bb-bdd7-c4274d12a67e"
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

### 2. Para ler os eletrodomésticos cadastrados, utilizar uma requisição do tipo GET:

```bash
curl --location 'localhost:8080/eletrodomestico'
```

**Saída: retorna os eletrodomésticos cadastrados**

```json
[
    {
        "inicio_uso": null,
        "eletro_ligado": false,
        "fim_uso": null,
        "tempo_uso": 0.0,
        "nome": "Televisão",
        "potencia": 110,
        "modelo": "Electrolux",
        "idEletrodomestico": "e0e8fd0a-6619-4140-943e-ef2d3e871949",
        "endereco": {
            "rua": "Avenida 1",
            "numero": 20,
            "bairro": "Bairro 1",
            "cidade": "São Paulo",
            "estado": "São Paulo",
            "idEndereco": "209f52c5-d1a2-443f-bd41-d8a0ee014f22",
            "usuario": {
                "idUsuario": "d975d4d3-eb20-4dfe-b9a1-5113b8ebd2fe",
                "loginUsuario": "ricardoms"
            }
        }
    }
]
```

### 3. Para ler um eletrodoméstico específico, utilizar uma requisição do tipo GET, passando um id como parâmetro:

```bash
curl --location 'localhost:8080/eletrodomestico/c88f374b-7d7f-4f7b-a484-3d80301d2134'
```

**Saída: retorna o eletrodoméstico ou mensagem de erro, caso não haja um eletrodoméstico com o id especificado**

```json
{
    "inicio_uso": null,
    "eletro_ligado": false,
    "fim_uso": null,
    "tempo_uso": 0.0,
    "nome": "Televisão",
    "potencia": 110,
    "modelo": "Electrolux",
    "idEletrodomestico": "e0e8fd0a-6619-4140-943e-ef2d3e871949",
    "endereco": {
        "rua": "Avenida 1",
        "numero": 20,
        "bairro": "Bairro 1",
        "cidade": "São Paulo",
        "estado": "São Paulo",
        "idEndereco": "209f52c5-d1a2-443f-bd41-d8a0ee014f22",
        "usuario": {
            "idUsuario": "d975d4d3-eb20-4dfe-b9a1-5113b8ebd2fe",
            "loginUsuario": "ricardoms"
        }
    }
}
```

### 4. Para atualizar um eletrodoméstico, utilizar uma requisição do tipo PUT, passando as informações, incluindo o id do objeto a ser atualizado:

```bash
curl --location --request PUT 'localhost:8080/eletrodomestico' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "Geladeira",
    "potencia": 110,
    "modelo": "Electrolux",
    "idEletrodomestico": "e0e8fd0a-6619-4140-943e-ef2d3e871949",
    "endereco": {
        "idEndereco": "209f52c5-d1a2-443f-bd41-d8a0ee014f22"
    }
}
'
```
**Saída: objeto eletrodoméstico atualizado**

```json
{
    "inicio_uso": null,
    "eletro_ligado": false,
    "fim_uso": null,
    "tempo_uso": 0.0,
    "nome": "Geladeira",
    "potencia": 110,
    "modelo": "Electrolux",
    "idEletrodomestico": "e0e8fd0a-6619-4140-943e-ef2d3e871949",
    "endereco": {
        "rua": null,
        "numero": 0,
        "bairro": null,
        "cidade": null,
        "estado": null,
        "idEndereco": "209f52c5-d1a2-443f-bd41-d8a0ee014f22",
        "usuario": null
    }
}
```


### 5. Para excluir um eletrodoméstico, utilizar uma requisição do tipo DELETE, passando um id como parâmetro:
```bash
curl --location --request DELETE 'localhost:8080/eletrodomestico/c88f374b-7d7f-4f7b-a484-3d80301d2134'
```
**Saída: retorna 200 - OK ou mensagem de erro, caso não haja um eletrodoméstico com o id especificado**

![image](https://github.com/RMorelloS/Pos_Tech_Fase_1/assets/32580031/5d2e2132-f3eb-4fa3-9113-253bbade14d5)

### 6. Ligar ou desligar aparelho


## Cadastro de Pessoas


A API cadastro de pessoas permite armazenar as seguintes informações: nome, data de nascimento, sexo e parentesco com o usuário.

### 1. Para gravar uma pessoa, utilizar uma requisição do tipo POST, passando informações como:

```bash
curl --location 'localhost:8080/pessoa' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "Ricardo",
    "dataNascimento": "1998-05-29",
    "sexo": "M",
    "parentescoUsuario": "Irmão",
    "idPessoa": "aaaa"
}'
```

**Saída: retorno 200 - OK ou erro de validação, caso algum dos campos não atenda aos requisitos necessários**

**Em caso de sucesso:**
![image](https://github.com/RMorelloS/Pos_Tech_Fase_1/assets/32580031/470614c5-9f0c-4f43-9161-6a473d1393f2)


**Em caso de erro:**
```json
{
    "timestamp": "2023-06-25T21:27:10.164262400Z",
    "status": null,
    "error": "Erro na validação de campos",
    "message": "Erro na validação dos campos: {nome=Campo 'nome' é obrigatório e não pode estar vazio}",
    "path": "/pessoa"
}
```
### 2. Para ler as pessoas cadastradas, utilizar uma requisição do tipo GET:

```bash
curl --location 'localhost:8080/pessoa'
```
**Saída: retorna as pessoas cadastradas**

```bash
[
    {
        "nome": "Ricardo",
        "dataNascimento": "1998-05-29",
        "sexo": "M",
        "parentescoUsuario": "Irmão",
        "idPessoa": "baaa8e09-186a-4bc0-a241-49ecd17d28eb"
    }
]
```

### 3. Para ler as informações de uma pessoa específica, utilizar uma requisição do tipo GET, passando um id como parâmetro:

```bash
curl --location 'localhost:8080/pessoa/baaa8e09-186a-4bc0-a241-49ecd17d28eb'
```

**Saída: retorna a pessoa ou mensagem de erro, caso não haja uma pessoa com o id especificado**
```bash
{
    "nome": "Ricardo",
    "dataNascimento": "1998-05-29",
    "sexo": "M",
    "parentescoUsuario": "Irmão",
    "idPessoa": "baaa8e09-186a-4bc0-a241-49ecd17d28eb"
}
```

### 4. Para atualizar as informações de uma pessoa, utilizar uma requisição do tipo PUT, passando as informações, incluindo o id do objeto a ser atualizado:

```bash
curl --location --request PUT 'localhost:8080/pessoa' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "Ricardo",
    "dataNascimento": "1995-05-29",
    "sexo": "M",
    "parentescoUsuario": "Meio-irmão",
    "idPessoa": "baaa8e09-186a-4bc0-a241-49ecd17d28eb"
}
'
```
**Saída: objeto pessoa atualizado**

```json
{
    "nome": "Ricardo",
    "dataNascimento": "1995-05-29",
    "sexo": "M",
    "parentescoUsuario": "Meio-irmão",
    "idPessoa": "2c66e46b-69d2-44ce-b382-18211b309449"
}
```

### 5. Para excluir uma pessoa, utilizar uma requisição do tipo DELETE, passando um id como parâmetro:
```bash
curl --location --request DELETE 'localhost:8080/pessoa/2c66e46b-69d2-44ce-b382-18211b309449'
```
**Saída: retorna 200 - OK ou mensagem de erro, caso não haja uma pessoa com o id especificado**

![image](https://github.com/RMorelloS/Pos_Tech_Fase_1/assets/32580031/7bf66031-639b-4eef-94d9-2a713b7b918b)





