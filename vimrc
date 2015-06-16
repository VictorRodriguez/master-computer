set tabstop=4
set shiftwidth=4 
set expandtab 
set smarttab 
set showcmd 
set number 
set showmatch
set hlsearch
set incsearch 
set ignorecase 
set smartcase 
set backspace=2 
set autoindent 
set textwidth=79 
set formatoptions=c,q,r,t
set ruler 
set background=dark 
set mouse=r
set colorcolumn=80
filetype plugin indent on
syntax on
highlight OverLength ctermbg=red ctermfg=white guibg=#592929
match OverLength /\%81v.\+/
