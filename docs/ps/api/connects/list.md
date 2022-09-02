Get Connects
===================

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f536.png"/> &nbsp;
Method: <code>GET</code>

```
/api/ps/connects
```

<b>Code samples</b>

```
await fetch('/api/ps/connects', {
    method: 'GET'
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
| 500         | Internal Error |

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f197.png"/> &nbsp;
<b>Status: 200</b>

```
[
    {
        "id": 1,
        "email": "keygenqt@gmail.com",
        "isSent": false,
        "locale": "EN-en",
        "createAt": 1662116858894,
        "updateAt": 1662118625155
    }
]
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