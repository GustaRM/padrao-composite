"""Gera o diagrama UML (estilo ferramenta de desenho, fundo quadriculado)."""
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
from matplotlib.patches import Rectangle, FancyArrowPatch

LH, PAD, NAME_H = 0.52, 0.25, 0.75

def novo(W_UN, H_UN, w_in):
    global ax, fig
    fig, ax = plt.subplots(figsize=(w_in, w_in * H_UN / W_UN))
    ax.set_xlim(0, W_UN); ax.set_ylim(0, H_UN); ax.set_aspect("equal"); ax.axis("off")
    for i in range(W_UN + 1):
        ax.plot([i, i], [0, H_UN], color="#E3E3E3", lw=0.6, zorder=0)
    for j in range(H_UN + 1):
        ax.plot([0, W_UN], [j, j], color="#E3E3E3", lw=0.6, zorder=0)

def box(cx, top, w, name, stereo=None, attrs=(), methods=()):
    h_name = NAME_H * (2 if stereo else 1) + (0.15 if stereo else 0)
    h_attr = max(len(attrs), 1) * LH + PAD
    h_meth = max(len(methods), 1) * LH + PAD
    h = h_name + h_attr + h_meth
    x0, y0 = cx - w / 2, top - h
    ax.add_patch(Rectangle((x0, y0), w, h, fc="white", ec="black", lw=1.4, zorder=2))
    ax.plot([x0, x0 + w], [top - h_name] * 2, color="black", lw=1.2, zorder=3)
    ax.plot([x0, x0 + w], [top - h_name - h_attr] * 2, color="black", lw=1.2, zorder=3)
    ty = top - 0.45
    if stereo:
        ax.text(cx, ty, f"«{stereo}»", ha="center", va="center", fontsize=9, zorder=4)
        ty -= 0.65
    ax.text(cx, ty, name, ha="center", va="center", fontsize=11, fontweight="bold", zorder=4)
    for k, a in enumerate(attrs):
        ax.text(x0 + 0.25, top - h_name - PAD / 2 - LH * (k + .5), a, ha="left", va="center", fontsize=8.5, zorder=4)
    for k, m in enumerate(methods):
        ax.text(x0 + 0.25, top - h_name - h_attr - PAD / 2 - LH * (k + .5), m, ha="left", va="center", fontsize=8.5, zorder=4)
    return dict(cx=cx, cy=(top + y0) / 2, top=top, bottom=y0, left=x0, right=x0 + w)

def line(pts, dashed=False):
    xs, ys = zip(*pts)
    ax.plot(xs, ys, color="black", lw=1.3, zorder=1, linestyle=(0, (5, 4)) if dashed else "-")

def arrow(p0, p1, style, dashed=False):
    a = FancyArrowPatch(p0, p1, arrowstyle=style, mutation_scale=22 if "|" in style else 18, lw=1.3,
                        color="black", zorder=1, shrinkA=0, shrinkB=0,
                        linestyle=(0, (5, 4)) if dashed else "-")
    if "|" in style:
        a.set_facecolor("white")
    ax.add_patch(a)

def legenda(x, y, titulo, itens):
    ax.text(x, y, titulo, fontsize=9.5, fontweight="bold")
    for k, t in enumerate(itens):
        ax.text(x, y - 0.9 - k * 0.8, f"• {t}", fontsize=8.5)

def salvar(nome="diagrama-uml.png"):
    plt.savefig(nome, dpi=140, bbox_inches="tight", facecolor="white")
novo(64, 31, 22)
comp = box(34, 31, 24, "Componente", "interface", (), ["+ getNome(): String", "+ calcularFolhaPagamento(): double", "+ contarFuncionarios(): int", "+ exibir(nivel: int): String"])
func = box(13, 19, 22, "Funcionario", None,
           ["- nome: String", "- cargo: String", "- salario: double"],
           ["+ getNome(): String", "+ getCargo(): String", "+ getSalario(): double", "+ calcularFolhaPagamento(): double", "+ contarFuncionarios(): int", "+ exibir(nivel: int): String"])
dep = box(50, 19, 26, "Departamento", None,
          ["- nome: String", "- filhos: List<Componente>"],
          ["+ getNome(): String", "+ adicionar(c: Componente): Departamento", "+ remover(c: Componente): boolean", "+ getFilhos(): List<Componente>", "+ calcularFolhaPagamento(): double", "+ contarFuncionarios(): int", "+ exibir(nivel: int): String"])
cli = box(8, 31, 12, "Main", None, [], ["+ main(args)"])
# realização (tracejada, triângulo vazado)
arrow((func["cx"], func["top"]), (func["cx"], comp["bottom"] - 0.0), "-|>", True) if False else None
line([(func["cx"], func["top"]), (func["cx"], 15.0 + 8.9 - 0)], True) if False else None
yb = comp["bottom"] - 2.6
line([(func["cx"], func["top"]), (func["cx"], yb), (28, yb)], True)
line([(dep["cx"] - 6, dep["top"]), (dep["cx"] - 6, yb), (28, yb)], True)
arrow((28, yb), (28, comp["bottom"]), "-|>", True)
# composição/agregação: Departamento -> Componente (filhos 0..*)
line([(dep["cx"] + 8, dep["top"]), (dep["cx"] + 8, 28.4), (comp["right"] + 0, 28.4)])
arrow((dep["cx"] + 4, 28.4), (comp["right"], 28.4), "->")
ax.text(dep["cx"] + 8.4, 25.0, "filhos\n0..*", fontsize=9)
# dependência Main -> Componente
arrow((cli["right"], 29.4), (comp["left"], 29.4), "->", True)
legenda(1, 8.5, "Legenda", ["Componente = Component", "Funcionario = Leaf (folha)", "Departamento = Composite", "Realização: tracejada + triângulo vazado", "Associação com multiplicidade: filhos 0..*"])
salvar("diagrama-uml.png")
