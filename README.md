# ms-redis

Proyecto para demostrar el uso de Redis con Spring Boot.

## Endpoints de la API

A continuación se muestran los comandos `curl` para interactuar con los endpoints de la API.

**Nota:** Asegúrate de que la aplicación se esté ejecutando en `http://localhost:8080`.

### Obtener saldo de cuenta

Recupera los detalles del saldo de una cuenta por su ID.

```bash
curl -X GET http://localhost:8080/api/v1/account-balances/123-abc
```

### Guardar saldo de cuenta

Crea o actualiza el saldo de una cuenta.

```bash
curl -X POST http://localhost:8080/api/v1/account-balances \
-H "Content-Type: application/json" \
-d '{"accountId": "123-abc", "balance": 1500.75, "currency": "USD", "accountType": "CHECKING"}'
```

### Eliminar saldo de cuenta

Elimina el saldo de una cuenta de la caché por su ID.

```bash
curl -X DELETE http://localhost:8080/api/v1/account-balances/123-abc
```