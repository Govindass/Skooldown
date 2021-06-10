# Skooldown
The koolest skript addon for cooldowns

With this addon you can create cooldowns with good performance, without worrying of deleting variables, etc.
Expired cooldowns will be automatically deleted in a very optimized way.
Everything is made with maximum performance in mind, you should be able to safely have millions of cooldowns running simultaneously.

# Syntaxes

Start a cooldown
```
(create|start) [a] cooldown %string% for %timespan%

Example: start cooldown "example%player%" for 45 seconds
```
Stop a cooldown
```
(reset|stop|delete|clear) cooldown %string%"

Example: stop cooldown "example%player%"
```
Check if cooldown is over
```
cooldown %string% (is|has) (finished|over|done)
cooldown %string% is(n't| not) unfinished)
cooldown %string% is(n't| not) (finished|over|done)
cooldown %string% is unfinished

Example:
cooldown "example%player%" is over
```
Display cooldown time
```
set {_cd} to cooldown "example%player%"
send "This cooldown will be over in %{_cd}%"
```
Extend/Reduce existing cooldown
```
add 5 seconds to cooldown "example%player%"
if cooldown "example%player%" is not higher than 5 seconds:
  remove 5 seconds from cooldown "example%player%"
```


# Compatibility
- Should work on about any Minecraft version on which Skript works, cooldowns do not interact with Minecraft code in any way
- Incompatible with WolvSK addon cooldowns due to syntax conflict, just use Skooldown instead
