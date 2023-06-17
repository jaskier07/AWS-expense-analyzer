# AccountStatementParserLambda
Lambda maps account statement's transactions to Transaction object.

It gets account statement's file content encoded with Base64 and bank's type. File is parsed by proper bank's parser. Returned object is list of Transactions.