Get Article
===================

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f536.png"/> &nbsp;
Method: <code>GET</code>

```
/api/ps/articles/{ID}
```

<b>Code samples</b>

```
await fetch('/api/ps/articles/{ID}', {
    method: 'GET'
}).then(async (response) => {
    console.log(response)
})
```

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/26ab.png"/> &nbsp;
HTTP response status codes

| Status code | Description        |
|-------------|--------------------|
| 200         | OK                 |
| 404         | Resource not found |
| 500         | Internal Error     |

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f197.png"/> &nbsp;
<b>Status: 200</b>

```
{
    "id": 1,
    "category": "OTHER",
    "publicImage": "https://keygenqt.com/images/blog/601076d399c15.jpg",
    "title": "Title",
    "description": "Description",
    "content": "Content",
    "isPublished": false,
    "uploads": [
        {
            "id": 1,
            "fileName": "d523f3fb-a8fe-49d9-9e67-3ce8b3231037.png",
            "fileMime": "image/png",
            "originalFileName": "screenshot-17.png",
            "createAt": 1661592987307
        }
    ]
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