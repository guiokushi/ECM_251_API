# ECM_251_API
 
# API
 
## Projeto de um aplicativo de review de games em Kotlin utilizando o ktor.
### Banco de dados usado
MariaDB
### FRONT-END
Flutter

# Feito por:
* André Saad Bonito
* Daniel Scabar
* Guilherme Okushi

# Link para o Front-end:
https://github.com/DanielScabar/Front_ECM251.git

# Banco de dados
## Para o banco de dados utilizamos 4 tabelas para representar as informações contidas em uma avaliação:

## - Avaliação:
![image](https://user-images.githubusercontent.com/87087019/144476640-412785a6-00dc-4321-a3a7-74f98aae03de.png)
### A tabela avaliação utiliza o idReview como chave estrangeira para conectar determinado elemento da tabela Review e junto com a nota da avaliação  e da data de postagem formam um elemento de Avaliação.

## - Jogo:
![image](https://user-images.githubusercontent.com/87087019/144476730-84bea1cf-f1fe-481e-a9da-ef35c1a1f21b.png)
### Na tabela Jogo temos os cadastros dos jogos com suas principais características nome, empresa onde foi criado, plataforma e data de lançamento.

## - Review:
![image](https://user-images.githubusercontent.com/87087019/144476765-69a190ad-1676-4797-916b-24eb08954156.png)
### A tabela Review incorpora o corpo da avaliação com dados como o texto da avaliação, o id do jogo e o id do usuário que criou a avaliação.

## - Usuario:
![image](https://user-images.githubusercontent.com/87087019/144476863-2915264b-d80c-4b91-a984-0ea896891f36.png)
### Na tabela Usuario temos a criação de usuários para utilizar o sistema de criação de avaliações.

# Rotas:

## Para apresentar uma tabela específica utilizamos o /tabela/nome_da_tabela:
![image](https://user-images.githubusercontent.com/87087019/144483007-3f376828-ce37-4910-9df4-20165b800bac.png)
Exemplo da tabela Avaliacao

## Para apresentar um elemento específico utilizamos o /tabela/nome_da_tabela/id_do_elemento:
![image](https://user-images.githubusercontent.com/87087019/144483654-7bb6cb51-013a-40d5-81e2-cb39792796cd.png)
Exemplo da tabela Avaliacao

## Para criar um elemento nas tabelas utilizamos o /criar/nome_da_tabela e inserir os atributos na URL:
![image](https://user-images.githubusercontent.com/87087019/144483671-ffb98f9d-30b4-4435-9841-95e9607f63e7.png)
Exemplo da tabela Avaliacao

## Para atualizar um elemento nas tabelas utilizamos o /update/nome_da_tabela e inserir os atributos na URL:
![image](https://user-images.githubusercontent.com/87087019/144483698-11c2e9a4-ec45-46a7-bbcd-7b1764cf247d.png)
Exemplo da tabela Avaliacao

## Para deletar um elemento da tabela utilizamos o /delete/nome_da_tabela e inserir o id do elemento na URL:
![image](https://user-images.githubusercontent.com/87087019/144484136-4e7c6546-e0c3-48c0-ae06-7a96e58f72c2.png)
Exemplo da tabela Avaliacao
