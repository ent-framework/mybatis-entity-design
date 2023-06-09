/*******************************************************************************
 * Copyright (c) 2023. Licensed under the ApacheLicense,Version2.0.
 ******************************************************************************/

package io.entframework.med.idea;

import com.intellij.ide.structureView.*;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.editor.Editor;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MedPsiStructureViewFactory implements PsiStructureViewFactory {

    @Override
    public StructureViewBuilder getStructureViewBuilder(@NotNull PsiFile psiFile) {
        return new TreeBasedStructureViewBuilder() {
            @NotNull
            @Override
            public StructureViewModel createStructureViewModel(@Nullable Editor editor) {
                return new JdlStructureViewModel(psiFile, editor);
            }
        };
    }

}

class JdlStructureViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {

    public JdlStructureViewModel(@NotNull PsiFile psiFile, @Nullable Editor editor) {
        super(psiFile, editor, new JdlStructureViewElement(psiFile));
        withSuitableClasses(

        );
        withSorters(Sorter.ALPHA_SORTER);
    }

    @Override
    public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
        return false;
    }

    @Override
    public boolean isAlwaysLeaf(StructureViewTreeElement element) {
        return false;
    }

}

class JdlStructureViewElement implements StructureViewTreeElement {

    private final PsiElement element;

    public JdlStructureViewElement(@NotNull PsiElement element) {
        this.element = element;
    }

    @Override
    public PsiElement getValue() {
        return element;
    }

    @Override
    public void navigate(boolean requestFocus) {
        ((Navigatable) element).navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return ((Navigatable) element).canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return ((Navigatable) element).canNavigateToSource();
    }

    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        ItemPresentation presentation = ((NavigationItem) element).getPresentation();
        assert presentation != null;
        return presentation;
    }

    @Override
    public TreeElement @NotNull [] getChildren() {

        return EMPTY_ARRAY;
    }

}
