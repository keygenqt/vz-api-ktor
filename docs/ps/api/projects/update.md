Update Project
===================

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f536.png"/> &nbsp;
Method: <code>PUT</code>

```
/api/ps/projects/{ID}
```

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f518.png"/> &nbsp;
<b>Headers</b>

* <code>'Content-Type': 'application/json'</code>

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f518.png"/> &nbsp;
<b>Parameters</b>

* <code>title</code> - title project, *required*
* <code>description</code> - short description, *required*
* <code>titleRu</code> - title project, *optional*
* <code>descriptionRu</code> - short description, *optional*
* <code>category</code> - ANDROID, WEB, IOS, OTHER, *required*
* <code>publicImage</code> - url image, *required*
* <code>url</code> - url to website project, *optional*
* <code>urlGitHub</code> - url to website project, *optional*
* <code>urlSnapcraft</code> - url to website project, *optional*
* <code>urlDownload</code> - url to website project, *optional*
* <code>urlYouTube</code> - url to website project, *optional*
* <code>isPublished</code> - is public on website, *required*
* <code>uploads</code> - ids uploads for relations, *required*

<b>Code samples</b>

```
await fetch('/api/ps/projects/{ID}', {
    method: 'PUT',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        category: 'OTHER',
        publicImage: 'https://keygenqt.com/images/blog/601076d399c15.jpg',
        title: 'Title',
        url: 'https://api.keygenqt.com/',
        urlGitHub: 'https://github.com/keygenqt',
        urlSnapcraft: 'https://snapcraft.io/',
        urlDownload: 'https://keygenqt.com/files/601076d399c15.zip',
        urlYouTube: 'https://www.youtube.com/',
        description: 'Description',
        isPublished: false,
        uploads: [1, 2, 3], // ids uploads for relations
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
    "message": "Error update project, please check the correctness of data entry",
    "validate": [
        {
            "filed": "description",
            "errors": [
                "Size must be between 3 and 500",
                "Must not be null and not blank"
            ]
        },
        {
            "filed": "title",
            "errors": [
                "Size must be between 3 and 255",
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