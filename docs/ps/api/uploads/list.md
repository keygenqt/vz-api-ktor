Get Uploads
===================

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f536.png"/> &nbsp;
Method: <code>GET</code>

```
/api/ps/uploads
```

<b>Code samples</b>

```
await fetch('/api/ps/uploads', {
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
        "id": 58,
        "fileName": "e55d72df-6de6-4760-91cf-5c1f2310d74c.jpeg",
        "fileMime": "image/jpeg",
        "originalFileName": "pexels-anni-roenkae-3219899.jpg",
        "createAt": 1662103689887,
        "isRelationArticle": true,
        "isRelationProject": false
    },
    {
        "id": 57,
        "fileName": "5d6f8bf8-7a02-4b1b-8d3e-b74d00896b5a.png",
        "fileMime": "image/png",
        "originalFileName": "preview-600.png",
        "createAt": 1662103679976,
        "isRelationArticle": true,
        "isRelationProject": false
    },
    {
        "id": 56,
        "fileName": "20498164-ba4c-4770-8c75-80aa45cadcc6.xcf",
        "fileMime": "image/x-xcf",
        "originalFileName": "preview.xcf",
        "createAt": 1662103612370,
        "isRelationArticle": false,
        "isRelationProject": true
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