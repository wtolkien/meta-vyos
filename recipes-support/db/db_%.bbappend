# enable DB 1.85 compatibility API (required by arpd from iproute2
# package)
EXTRA_OECONF += "\
       	--enable-compat185 \
        "
