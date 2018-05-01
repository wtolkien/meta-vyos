SUMMARY = "The VyOS Router ISO Image"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

NOISO = "0"
NOHDD = "1"

IMAGE_LINGUAS = ""

inherit core-image

IMAGE_ROOTFS_SIZE ?= "8192"

# general
IMAGE_INSTALL = "\
    base-files \
    base-passwd \
    busybox \
    udev \
    netbase \
    e2fsprogs-mke2fs \
    grub \
    kernel-modules \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    "

# Disable some default settings
VIRTUAL-RUNTIME_keymaps = ""
SYSVINIT_SCRIPTS = ""

IMAGE_FSTYPES = "squashfs"

# As we use squashfs, we have to set read-only-rootfs
EXTRA_IMAGE_FEATURES += "read-only-rootfs"

NO_RECOMMENDATIONS = "1"

# Yocto 2.1 workaround
# Resolve dependency from poky/meta/classes/image-live.bbclass
do_image_ext4() {
    bbnote "vyos-iso-image.bb: ignoring do_image_ext4()!"
}
addtask image_ext4

# Tweak SYSLINUX commandline for a bootable image
SYSLINUX_KERNEL_ARGS_append = "rootimage=rootfs.img rootfstype=squashfs rdinit=/init debugshell=10"

ROOTFS_IMAGE = "vyos-image"
ROOTFS = "${DEPLOY_DIR_IMAGE}/${ROOTFS_IMAGE}-${MACHINE}.squashfs"
do_bootimg[depends] += "${ROOTFS_IMAGE}:do_image_complete"

# no packages required
deltask package
deltask packagedata
deltask package_write_ipk
deltask package_write_deb
deltask package_write_rpm
deltask package_write
