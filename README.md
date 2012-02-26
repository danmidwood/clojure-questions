# clojure-questions

This is a tiny little twitter bot that tweets new stackoverflow
questions that are tagged with 'clojure'. It uses the stackoverflow API
to poll for new questions every 5 minutes and tweets them.

The bot is running under the name
[@ClojureQs](http://twitter.com/ClojureQ).

## Usage

Because of how insane oauth is, if you want to run this bot you'll need
to obtain about 50 thousand different access tokens, keys, blah, etc.
The easiest way to do it is to create a new twitter account specifically
for the bot, then register a new application for the bot, and then
generate user tokens for that application. That'll give you everything
you need.

You'll need to drop all of these keys in a file called `creds.clj`. It
looks like this:

```clojure
{:consumer-key ""
 :consumer-token ""
 :user-token ""
 :user-secret ""}
```

## License

Copyright © 2012 Anthony Grimes

Distributed under the Eclipse Public License, the same as Clojure.
