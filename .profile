export JAVA_HOME=`/System/Library/Frameworks/JavaVM.framework/Versions/A/Commands/java_home -v "1.8"`
PATH=${JAVA_HOME}/bin:${PATH}
alias ls='ls -G'
alias la='ls -G -a'
alias ll='ls -G -l'
alias ..='cd ..'
export LSCOLORS=gxfxcxdxbxegedabagacad