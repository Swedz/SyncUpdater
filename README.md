# SyncUpdater

Tired of having to drag all your updated plugins into each servers plugin directory every time you make a change or fix? Well I've got news for you! I've had the same problem. I made this plugin which has been used on my server for a while and I decided it would behoove of me to upload it online for others to use too!

After installing the plugin into your plugins directory, run the server and shut it down so it generates the configuration file. It is suggested for all of your servers (that are on the same machine) to use the same config. It is also suggested to change the "directory" option in the config. This is the path to the directory that will store all of your plugins to get updated on all servers. **Do note that the plugins will only be updated on servers that already have the plugin installed on it. The file name for the plugin must also be the same in both the server(s) and the update directory.**

Here is the full default configuration file with comments:
```
# Whether or not updates should be logged and sent to players with permission syncupdater.verbose (this does not include reboot timer messages)
verbose: true
# There are 3 modes, AUTOMATIC, REDIS and MANUAL.
#  AUTOMATIC will automatically check the update directory for new updates
#  REDIS will only check for updates when requested via "/syncupdater publishredisupdate" (this will check for updates on all linked servers at once)
#  MANUAL will only check for updates when requested via "/syncupdater update" (this will only run on the server it was run on)
mode: AUTOMATIC
# This is the directory that updates will be checked for in
directory: '/root/syncupdater/updates'
# This is how long it takes for the reboot to take place, in seconds
reboot-timer: 30
# This defines all of the commands to be executed when the reboot is finishing
reboot-commands:
  - 'stop'
# These are the messages for different things that get sent - pretty straight forward
messages:
  no-permission: '&cI''m sorry, but you do not have permission to perform this command.
    Please contact the server administrators if you believe that this is in error.'
  updated: '&c Recognized server update! Rebooting as soon as possible.'
  countdown: '&c Server rebooting in %time%.'
  rebooting: '&c Server rebooting...'
# Settings for the AUTOMATIC mode
automatic:
  # How frequent (in seconds) the plugin will check for updates in the update directory
  interval: 10
# Settings for the REDIS mode
redis:
  # Your redis login credentials
  hostname: localhost
  port: 6379
  password: password
```

Here is a list of the commands you can use:
- **/syncupdater help**
  - Displays all of the commands available.
- **/syncupdater reload**
  - Reloads the configuration file.
- **/syncupdater update**
  - Run an update check for all plugins.
