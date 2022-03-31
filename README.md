###  При создании БД проливаются тестовые данные:

testUser1, testUser1.com счет 1, 'A1', 10000.00, 1 

testUser2, testUser2.com счет 2, 'A2', 10000.00, 1

### Контроллер для тестирования переводов:

http://localhost:8080/perform-transfer

### Запрос для перевода:
{
"sourceBankBookNumber": "A1",
"targetBankBookNumber": "A2",
"amount": 55
}