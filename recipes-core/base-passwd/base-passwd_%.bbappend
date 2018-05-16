# remove patch that uses /bin/sh for root, and keep root locked
# (we want /bin/bash, otherwise ~/.bashrc does not get sourced)
SRC_URI_remove = " \
    file://nobash.patch \
    file://noshadow.patch \
    "
