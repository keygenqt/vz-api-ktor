Refresh Token
===================

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f536.png"/> &nbsp; 
Method: <code>POST</code>

```
/api/ps/refresh
```

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f518.png"/> &nbsp;
<b>Headers</b>

* <code>'Content-Type': 'application/json'</code>

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f518.png"/> &nbsp;
<b>Parameters</b>

* <code>deviceId</code> - Unique application key, *required*
* <code>refreshToken</code> - Token received during authorization through JSON Web Tokens, *required*

<b>Code samples</b>

```
await fetch('/api/ps/refresh', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        deviceId: 'Unique key',
        refreshToken: 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSZWZyZXNoIiwiaXNzIjoia3Rvci5pbyIsImlkIjoxLCJpYXQiOjE2NjE1ODczMjR9.e1Vk13VsJihTPQI_uKn52Pe0B3EpqXEgE3i_GVkeH5nXHl01cOAOApuPyT1sAAW1ALD3h2WJhZBDjAJyBkJbcQ',
    })
}).then(async (response) => {
    console.log(response)
})
```

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/26ab.png"/> &nbsp;
HTTP response status codes

| Status code | Description    |
|-------------|----------------|
| 200         | OK             |
| 400         | Bad Request    |
| 500         | Internal Error |

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f197.png"/> &nbsp;
<b>Status: 200</b>

```
{
    "id": 1,
    "email": "guest@keygenqt.com",
    "role": "GUEST",
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcyI6Imt0b3IuaW8iLCJpZCI6MSwiZXhwIjo0OTg1MDE2ODg2Nn0.uJxs8eep75HGywROEHcKuGX7l6NRledfv0pQg2s1QUJtqg7oYjPHtvR8oHYIAH05_c4xtoS5wuE02wjGpwqPnQ",
    "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSZWZyZXNoIiwiaXNzIjoia3Rvci5pbyIsImlkIjoxLCJpYXQiOjE2NjE1ODc0OTl9.bv4dSsbWST2eZdj_m5K-7dYjVOL_jKL3lcfDjXelu5cCNKfgWZFiLzLcKd7sVmzzSzH3SFSEr9iCBJTpDpXTNg"
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
<b>Status: 500</b>

```
{
    "code": 500,
    "message": "Internal Error"
}
```