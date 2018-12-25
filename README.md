# sd-data-concurrence-1
Repositório para exercício 1 sobre concorrência na manipulação de dados

### Solução

Foi usado uma tabela auxiliar chamada aux_lock, onde através dela cada instância da aplicação reserva um id antes do processo de canalização (INSERT, UPDATE, DELETE). Dessa forma quando qualquer outra instância for iniciar seu processo de canalização, ela deve recuperar o último id reservado, incrementá-lo e também reservá-lo. Para que esse procedimento entre as instâncias ocorra de forma sincronizada com relação a comunicação com o  banco de dados, foi usado um arquivo conhecido entre as instâncias que nomeamos de FileLock.  

### Para executar realize os seguintes passos:

> - crie as tabelas no banco de dados utilizando o **SQL** presente no arquivo **tables.sql**
> - execute **mvn package** na raiz do projeto
> - execute o arquivo **sh** **start-instances.sh**