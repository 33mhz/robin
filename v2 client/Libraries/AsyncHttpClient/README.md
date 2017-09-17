#AsyncHttpClient by Callum Taylor

Follow me on [Twitter](http://twitter.com/scruffyfox) | [App.net](http://alpha.app.net/scruffyfox) | [Website](http://callumtaylor.net) | [Blog](http://blog.callumtaylor.net)

This is the new and improved version of `AsyncHttpClient` taken from X-Library. It was long due a re-write.

In this version it allows a more flexible usage of posting files, http entities and GZIP handling.

The library uses Apache's HttpRequest library.

It consists of 2 different classes, `AsyncHttpClient` and `SyncHttpClient`. Obviously by the name, `AsyncHttpClient` is for asynchronous requests which uses the `AsyncTask` paradigm, and `SyncHttpClient` is for synchronous requests which should be handled **by yourself in a thread outside of the UI thread**.

#Table of contents

- AsyncHttpClient
	1. [Example GET](docs/async-get.md)
	2. [Example POST](docs/async-post.md)
	3. [Example PUT](docs/async-put.md)
	4. [Example DELETE](docs/async-delete.md)
	5. [Example custom handler](docs/async-custom.md)
	
- SyncHttpClient
	1. [Example GET](docs/sync-get.md)
	2. [Example POST](docs/sync-post.md)
	3. [Example PUT](docs/sync-put.md)
	4. [Example DELETE](docs/sync-delete.md)
	5. [Example custom handler](docs/sync-custom.md)
	

#Other notes
###Downloading large files

In order to download large files, you will need to subclass `AsyncHttpResponseHandler` and override the `onPublishedDownloadProgress()` method to write directly to cache instead of appending to a `ByteArrayOutputStream` which is what the standard `BinaryResponseHandler` does. This is to stop OOM due to a over-sized output stream.

###AsyncHttpClient

**Note:** Because `AsyncHttpClient` uses `AsyncTask`, only one instance can be created at a time. If one client makes 2 requests, the first request is canceled for the new request. You can either wait for the first to finish before making the second, or you can create two seperate instances. See: [Example custom handler](docs/async-custom.md) for more.

###SyncHttpClient

`SyncHttpClient` is a paramitized class which means the type you infer to it, is the type that gets returned when calling the method. When supplying a [Processor](https://github.com/scruffyfox/AsyncHttpClient/blob/v1.3/src/net/callumtaylor/asynchttp/processor/Processor.java), that processor must also paramitized with the same type as the `SyncHttpClient` instance.

You can also get the info of the request by calling your `SyncHttpClient` instance and `getConnectionInfo()`. This can only be called after the response has been completed.

**Note:** Because Android requires all network requests to be performed outside the UI thread, you must use SyncHttpClient sparingly and make sure you handle the operaion **OFF** the UI thread.

Because of the nature of REST, `GET` and `DELETE` requests behave in the same
way, `POST` and `PUT` requests also behave in the same way.