# Serbia 2016. Elections Tweet Collection
-----

### Description
This repository holds files, scripts and programs used for obtaining tweets during the 2016. Serbia Parliamentary, local and AP elections.

This is a work in progress, without any particular goal or interest.

### Contents
1. [Collected tweets](https://github.com/salex89/SerbiaElectionScraper/raw/master/tweets/tweets.txt.zip)

Description: Zipped textual file with election related tweets (hashtags #izbori2016 and #избори2016) obtained from the [Twitter Streaming API](https://dev.twitter.com/streaming/overview) starting from 24.04.2016 02:00 until 25.04.2016 09:00. 

2. [Java-based Stream Consumer](https://github.com/salex89/SerbiaElectionScraper/tree/master/elections-scraper)

Description: Simple Java application consuming tweets with the relevant hashtags and storing them into a file. Note, this can be achieved with a much simpler app, or even curl, but I didn't want to risk it and lose the connection midway without noticing, rendering the attempt useless.

### License
All content here is completely free to use for any purpose... But let me know if you do anything cool, I would like to see it :) .
