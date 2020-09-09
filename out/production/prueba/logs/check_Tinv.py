import re
import sys
import os

# abro archivo 
f = open(os.path.join(os.getcwd(),"exec_t.txt"), "r")
line= f.readline()
f.close()

w= open(os.path.join(os.getcwd(),"res_TInv.txt"),"a")

NUM_TAREAS=line.count("T0")
regex = "^(T0)(?:.)*?(?:(?:(T1)(?:.)*?(T3)(?:.)*?(?:(?:(T5)(?:.)*?(T9)(?:.)*?)|(?:(T7)(?:.)*?))(?:(?:(T11)(?:.)*?(T15))|(?:(T13)(?:.)*?(T16))))|(?:(T2)(?:.)*?(T4)(?:.)*?(?:(?:(T6)(?:.)*?(T10)(?:.)*?)|(?:(T8)(?:.)*?))(?:(?:(T12)(?:.)*?(T15))|(?:(T14)(?:.)*?(T16)))))"
i=0

while i<NUM_TAREAS:
    aux=re.findall(regex,line) #me devuelve una lista de un elemento con la tupla ('T0','','T1',...etc)
    a=[]

    if len(aux)==0:
        print("Funcionamiento incorrecto")
        break

    w.write("El numero de Transiciones es:" + str(line.count("T"))+"\n")
    # filtro la lista encontrada por un array sin espacios vacios (a)
    for element in aux[0]:
        if element != '':
            a.append(element)

    w.write("Se extrae el siguiente invariante T de la cadena: ")
    w.write(''.join(a)+"\n")
    # De cada elemento en el array lo busco en la sucesion de T disparadas (string) y las retiro del string
    for e in a:
        st=line.find(e)
        end=st+len(e)
        line=line[0:st]+line[end:len(line)]

    w.write("La cadena queda: "+line+"\n")
    w.write("*************************\n")
    i=i+1

    if i==NUM_TAREAS:
        if line.count("T")==0:
            print("Funcionamiento CORRECTO segun los T-Invariantes")
        else:
            print("Funcionamiento incorrecto")

w.close()
