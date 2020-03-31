package org.minbox.framework.little.bee.core.command;

import org.minbox.framework.little.bee.core.LittleBeeCommandException;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.File;

/**
 * Git pull command implementation
 *
 * @author 恒宇少年
 */
public class GitPullCommand extends GitCommand {
    /**
     * project name
     */
    private String projectName;
    /**
     * project remote name
     */
    private String remote = "origin";
    /**
     * clone the branch of the project, the default is master
     */
    private String branch = "master";

    /**
     * Set the remote name of the project
     *
     * @param remote remote name
     */
    public void setRemote(String remote) {
        if (!ObjectUtils.isEmpty(remote)) {
            this.remote = remote;
        }
    }

    /**
     * Set the project name
     *
     * @param projectName project name
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Set the branch of the project
     *
     * @param branch project branch name
     */
    public void setBranch(String branch) {
        if (!ObjectUtils.isEmpty(branch)) {
            this.branch = branch;
        }
    }

    /**
     * Pre command execution
     * execution command format is "git pull origin master"
     * <p>
     * use {@link #checkProjectExist(String)} method check if the project directory exists
     * <p>
     * use {@link #setExecutionDirectory(String)} method set the execution directory of the command to the directory of the project
     */
    @Override
    void preExecute() {
        Assert.notNull(this.projectName, "Project name cannot be null.");
        String projectPath = getProjectDirectory() + projectName;
        checkProjectExist(projectPath);
        setExecutionDirectory(projectPath);
        setCommandOptions(new String[]{GIT_PULL, remote, branch});
    }

    /**
     * Check project exist
     * <p>
     * throws an exception if the project directory does not exist or is not a directory
     *
     * @param projectPath project directory path
     */
    private void checkProjectExist(String projectPath) {
        File file = new File(projectPath);
        if (!file.exists() || !file.isDirectory()) {
            throw new LittleBeeCommandException("Project \"" + projectPath + "\" does not exist");
        }
    }
}