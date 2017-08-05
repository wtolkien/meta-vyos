PACKAGECONFIG ??= "cap openssl \
    ${@bb.utils.filter('DISTRO_FEATURES', 'ipv6', d)} \
"
