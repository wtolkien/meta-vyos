SUMMARY = "The VyOS Router Image"

IMAGE_INSTALL = " \
    packagegroup-core-boot \
    packagegroup-vyos \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    "

IMAGE_LINGUAS = " "

LICENSE = "MIT"

inherit core-image

# since there will be a persistent data partition, we can easily
# get away with reducing rootfs size overhead to 20%
IMAGE_OVERHEAD_FACTOR = "1.2"

IMAGE_INSTALL_append = " \
    kernel-modules \
    "

# create home directory for vyos user
python do_rootfs_append () {
    bb.build.exec_func('add_vyos_home_dir', d)
}

add_vyos_home_dir() {
    install -d ${IMAGE_ROOTFS}/home
    install -o vyos -g users -d ${IMAGE_ROOTFS}/home/vyos
}
