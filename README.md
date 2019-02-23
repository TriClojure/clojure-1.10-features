# Clojure 1.10 features demo

## Running the examples

If an example has a `-main` function, it can be run from the command-line like
this (using `src/demo/repl.clj` as an example):

```bash
$ clj -m demo.repl
```

Some examples (e.g. `demo.tap`) do not have a `-main` function and are intended
to be run from the REPL, for example:

```clojure
$ clj
Clojure 1.10.0
user=> (require 'demo.tap)
nil
user=> (add-tap demo.tap/tap-handler)
nil
user=> (tap> :hello)
true
user=> :tap 1 :hello

user=> (tap> 42)
true
:tap 2 42
user=> (remove-tap demo.tap/tap-handler)
nil
user=> (tap> 43)
true
```

Or, better yet, connect your editor to a REPL, open the source file, and
evaluate the forms in the `comment` block.

## Examples

### `demo.tap`

A demonstration of `add-tap`, `remove-tap`, and `tap>`, using a handler that
prints the keyword `:tap`, an incrementing counter value, and the value that was
sent to the tap.

### `demo.repl`

A demonstration of `clojure.main/repl`, which is NOT a new feature in Clojure
1.10, but is useful for comparison to the new `prepl` feature in Clojure 1.10.

`clojure.main/repl` takes options that allows you to customize how the REPL
behaves, although `prepl` offers lower-level access to the REPL output,
providing a higher degree of flexibility in customization.

### `demo.prepl`

A demonstration of `clojure.core.server/prepl` that runs within a single
process. The REPL server is run on a background thread, while the main thread
asks for input (forms to be evaluated) from the user.

### `demo.io-prepl`

A demonstration of `clojure.core.server/io-repl`, a convenience layer on top of
`prepl` suitable for use over a network via e.g. a socket REPL.

After starting the REPL server by running `clj -m demo.io-repl`, you can connect
from another REPL using `clojure.core.server/remote-prepl` and start sending
over forms to be evaluated.
