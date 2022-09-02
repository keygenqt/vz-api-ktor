Get Projects
===================

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f536.png"/> &nbsp;
Method: <code>GET</code>

```
/api/ps/projects/all
```

<b>Code samples</b>

```
await fetch('/api/ps/projects/all', {
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
        "title": "Title",
        "description": "Description",
        "titleRu": "Title",
        "descriptionRu": "Description",
        "category": "OTHER",
        "publicImage": "https://keygenqt.com/images/blog/61337b11b1300.jpg",
        "url": "https://api.keygenqt.com/",
        "urlGitHub": "https://github.com/keygenqt",
        "urlSnapcraft": 'https://snapcraft.io/',
        "urlDownload": 'https://keygenqt.com/files/601076d399c15.zip',
        "urlYouTube": 'https://www.youtube.com/',
        "isPublished": false,
        "isLike": false,
        "createAt": 1661533680528,
        "updateAt": 1661533680528,
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