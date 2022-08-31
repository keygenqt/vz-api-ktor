Like Project
===================

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f536.png"/> &nbsp;
Method: <code>POST</code>

```
/api/ps/projects/like/{ID}
```

<b>Code samples</b>

```
await fetch('/api/ps/projects/like/{ID}', {
    method: 'POST',
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
| 404         | Resource not found |
| 500         | Internal Error     |

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f197.png"/> &nbsp;
<b>Status: 200</b>

```
{
    "id": 31,
    "projectId": 3,
    "key": "421aa90e079fa326b6494f812ad13e79",
    "createAt": 1661929660607
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