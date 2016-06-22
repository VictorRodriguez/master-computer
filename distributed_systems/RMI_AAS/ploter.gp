set terminal pdf
set output 'out.pdf'
set xlabel 'clients'
set ylabel 'presition'
set zlabel 'time'
splot "data.dat" title 'Main Function' with lines
