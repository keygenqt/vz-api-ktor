Auth Session
===================

<img style="height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f536.png"/> &nbsp;
Method: <code>POST</code>

```
/api/ps/auth
```

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f518.png"/> &nbsp;
<b>Headers</b>

* <code>'Content-Type': 'application/json'</code>

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f518.png"/> &nbsp;
<b>Parameters</b>

* <code>deviceId</code> - Unique application key, *required*
* <code>email</code> - Unique login user, *required*
* <code>password</code> - Secret key for authentication, *required*

<b>Code samples</b>

```
await fetch('/api/ps/auth', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        deviceId: 'Unique key',
        email: 'guest@keygenqt.com',
        password: '12345678'
    })
}).then(async (response) => {
    console.log(response)
})
```

<div style="padding-top: 10px">
<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/26ab.png"/> &nbsp;
HTTP response status codes
</div>


| Status code | Description    |
|-------------|----------------|
| 200         | OK             |
| 400         | Bad Request    |
| 422         | Form validate  |
| 500         | Internal Error |

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f197.png"/> &nbsp;
<b>Status: 200</b>

```
{
    "id": 1,
    "email": "guest@keygenqt.com",
    "role": "GUEST"
}
```

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f534.png"/> &nbsp;
<b>Status: 400</b>

```
{
    "code": 400,
    "message": "Bad Request"
}
```

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f534.png"/> &nbsp;
<b>Status: 422</b>

```
{
    "code": 422,
    "message": "Unprocessable Entity",
    "validate": [
        {
            "filed": "password",
            "errors": [
                "Size must be between 8 and 12"
            ]
        }
    ]
}
```

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f534.png"/> &nbsp;
<b>Status: 500</b>

```
{
    "code": 500,
    "message": "Internal Error"
}
```

<style>
  .md-content__button {
    display: none;
  }
</style>
