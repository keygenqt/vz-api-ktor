Create Article
===================

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f536.png"/> &nbsp;
Method: <code>POST</code>

```
/api/ps/articles
```

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f518.png"/> &nbsp;
<b>Headers</b>

* <code>'Content-Type': 'application/json'</code>

<img style="max-height: 13px;" src="https://github.githubassets.com/images/icons/emoji/unicode/1f518.png"/> &nbsp;
<b>Parameters</b>

* <code>category</code> - ANDROID, WEB, IOS, OTHER, *required*
* <code>publicImage</code> - url image, *required*
* <code>title</code> - title article, *required*
* <code>description</code> - short description, *required*
* <code>content</code> - content article, *required*
* <code>isPublished</code> - is public on website, *required*
* <code>uploads</code> - ids uploads for relations, *required*

<b>Code samples</b>

```
await fetch('/api/ps/articles', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        category: 'OTHER',
        publicImage: 'https://keygenqt.com/images/blog/601076d399c15.jpg',
        title: 'Title',
        description: 'Description',
        content: 'Content',
        isPublished: false,
        uploads: [1, 2, 3], // ids uploads for relations
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
| 422         | Form validate  |
| 500         | Internal Error |

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
    "message": "Error update article, please check the correctness of data entry",
    "validate": [
        {
            "filed": "content",
            "errors": [
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