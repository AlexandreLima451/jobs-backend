# Simples Dental

##OBJETIVO

Desenvolva uma API que execute o CRUD completo atráves de uma interface REST que atenda aos requisitos descritos seção [teste](#TESTE). Você deve desenvolver em JAVA utilizando o framework [Play Framework](https://www.playframework.com/documentation/2.4.x/NewApplication) e banco de dados [Postgresql](http://www.postgresql.org/) utilize Hibernate ou queries nativas para manuplas o banco de dados.

##ENTREGA

Faça um fork deste repositório suba seu nele depois abra um pull-request para que possamos da inicio a avaliação, Obrigado e boa divertimento 💪💪.

##TESTE

####Contatos

CHEMA DE CONTATO
  nome: String
  contato: String

#####API HTTP INTERFACE
```
GET /contacts
  params
    q: Busca
      RETURN Lista de contatos que contenham o texto passado em qualquer um dos seus atributos.
    fields: List<String>
      RETURN Lista de contatos apenas com os fields passados.
RETURN Lista de profissionais.

GET /contatcts/:id
RETURN Recupera os contato que atende ao ID indicado.

POST /contacts
RETURN Sucesso contato cadastrado.

PUT /contacts/:id
RETURN Altera o contato que atende ao ID indicado.

DELETE /contacts/:id
RETURN Sucesso contato excluído
````




####Profissionais

SCHEMA DE PROFISSIONAL
  nome: String
  cargo: ENUM
    0: Desenvolvedor
    1: Designer
    2: Suporte
    3: Tester
  nascimento: Date
  create_data: Date
  contatos: List<SCHEMA DE CONTATO>

#####API HTTP INTERFACE
```
GET /professeonals
  params
    q: Busca
      RETURN Lista de profissionais que contenham o texto passado em qualquer um dos seus atributos.
    fields: List<String>
      RETURN Lista de profissionais apenas com os fields passados.
RETURN Lista de profissionais.

GET /professeonals/:id
RETURN O profissional que atende ao ID indicado.

POST /professeonals
RETURN Sucesso profissional cadastrado.

PUT /professeonals/:id
RETURN Altera o profissional que atende ao ID indicado.

DELETE /professeonals/:id
RETURN Sucesso profissional excluído

/**
* Ao criar um profissional os contatos também são criados.
 * Ao editar um profissional os contatos também são editados.
 */
```
