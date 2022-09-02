Update Connect
===================

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f536.png"/> &nbsp;
Method: <code>PUT</code>

```
/api/ps/connects/{ID}
```

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f518.png"/> &nbsp;
<b>Headers</b>

* <code>'Content-Type': 'application/json'</code>

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f518.png"/> &nbsp;
<b>Parameters</b>

* <code>email</code> - email for connect, *required*
* <code>locale</code> - EN-en or RU-ru, *required*
* <code>isSent</code> - Connect or not, *required*

<b>Code samples</b>

```
await fetch('/api/ps/connects/{ID}', {
    method: 'PUT',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        email: 'keygenqt@gmail.com',
        locale: 'EN-en',
        isSent: true,
    })
}).then(async (response) => {
    console.log(response)
})
```

<div style="padding-top: 10px">
<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/26ab.png"/> &nbsp;
HTTP response status codes
</div>

| Status code | Description        |
|-------------|--------------------|
| 200         | OK                 |
| 400         | Bad Request        |
| 404         | Resource not found |
| 422         | Form validate      |
| 500         | Internal Error     |

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f197.png"/> &nbsp;
<b>Status: 200</b>

```
{
    "id": 1,
    "email": "keygenqt@gmail.com",
    "isSent": false,
    "locale": "RU-ru",
    "createAt": 1662116865969,
    "updateAt": 1662118626855
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
<b>Status: 404</b>

```
{
    "code": 404,
    "message": "Resource not found"
}
```

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f534.png"/> &nbsp;
<b>Status: 422</b>

```
{
    "code": 422,
    "message": "Error creating connect, please check the correctness of data entry",
    "validate": [
        {
            "filed": "email",
            "errors": [
                "Must not be null and not blank"
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