#!/usr/bin/env bash
ps ax | grep com.sumscope.cdh.webbond.App | grep -v grep | awk '{print $1}' | xargs kill