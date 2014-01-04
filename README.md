# clojure-questions

This is a tiny little twitter bot that uses the StackOverflow API to tweet new stackoverflow questions that are tagged with 'clojure'.

The bot is running under the name
[@ClojureQs](http://twitter.com/ClojureQs).

## Usage

Because of how insane oauth is, if you want to run this bot you'll need to obtain about 50 thousand different access tokens, keys, blah, etc. The easiest way to do it is to create a new twitter account specifically for the bot, then register a new application for the bot, and then generate user tokens for that application. That'll give you everything
you need.

Bitly also requires an access token. If you're just playing with this then it's easier to no-op the `shorten` function instead.

For development you can use [environ](https://github.com/weavejester/environ) to manage the tokens, drop all of these keys into your leiningen profile (~/.lein/profiles.clj). It should look like this:

```clojure
{:user
 {:env {:clojureqs-app-consumer-key ""
        :clojureqs-app-consumer-secret ""
        :clojureqs-user-access-token ""
        :clojureqs-user-access-token-secret ""
        :bitly-access-token ""}}}

```

## License

Copyright © 2012, 2014 Anthony Grimes, Dan Midwood

Distributed under the Eclipse Public License, the same as Clojure.
